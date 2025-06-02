import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { CartItem } from './cart-item';
import { DiscountCartItem } from './discount-cart-item';


@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  cartItems: CartItem[] = [
    {
      id: 'a8098c1a-f86e-11da-bd1a-00112444be1e',
      orderId:'f47ac10b-58cc-4372-a567-0e02b2c3d479',
      name: 'Samsung',
      price: 9999.99,
      discountedPrice: 9999.99,
      quantity: 1,
      imageUrl: '',
    },
    {
      id: 'c56a4180-65aa-42ec-a945-5fd21dec0538',
      orderId: 'f47ac10b-58cc-4372-a567-0e02b2c3d479',
      name: 'Chair',
      price: 49.99,
      discountedPrice: 49.99,
      quantity: 2,
      imageUrl: '',
    },
  ];


  private readonly http = inject(HttpClient);
  private readonly path = "/api/v1/order";

  private readonly _items = signal<CartItem[]>(this.cartItems);

  public readonly items = this._items.asReadonly();

  constructor() { }

  public applyDiscount(discountCode: string, orderId: string): Observable<DiscountCartItem | Error> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'X-Discount-Code': discountCode,
    });
    return this.http.post<DiscountCartItem | Error>(`${this.path}/${orderId}/discount`, {}, { headers })
      .pipe(
        catchError((err) => {
          console.error(err);
          return of(new Error('Failed to proceed with discount application'));
        }),
        tap((resp) => {
          if(!(resp instanceof Error)){
            const currentItems = this._items();
            const updatedItems: CartItem[] = currentItems.map((item) => {
              const backendItem = resp.items.find(b => b.productName === item.name);

              if (backendItem && backendItem.isDiscounted) {
                return {
                  ...item,
                  discountedPrice: backendItem.price
                };
              }
              return item;
            });

            this._items.set(updatedItems);
          }

        }),
    );
}
}
