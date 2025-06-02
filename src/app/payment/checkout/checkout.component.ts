import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CartItem } from '../data-realm/cart-item';
import { PaymentService } from '../data-realm/payment.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
  ],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {
    cartItems: CartItem[] = [
      {
        id: 'a8098c1a-f86e-11da-bd1a-00112444be1e',
        orderId:'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        name: 'Samsung',
        price: 9999.99,
        quantity: 1,
        imageUrl: '',
      },
      {
        id: 'c56a4180-65aa-42ec-a945-5fd21dec0538',
        orderId: 'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        name: 'Chair',
        price: 49.99,
        quantity: 2,
        imageUrl: '',
      },
    ];

    private readonly paymentService = inject(PaymentService);
    form: FormGroup;
    discountCode: string = '';
    discountApplied: boolean = false;

    countryOptions: string[] = ['Morocco', 'Spain', 'France', 'Italy'];
    provinceOptions: string[] = [
      'Casablanca-Settat',
      'Marrakech-Tansift',
      'Rabat-Sale'
    ];

    constructor(private formBuilder:FormBuilder){
      this.form = this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        saveInfo: [false],
        cardNumber: [
          '',
          [Validators.required, Validators.pattern(/^\d{16}$/)]
        ],
        expiry: [
          '',
          [Validators.required, Validators.pattern(/^(0[1-9]|1[0-2])\/\d{2}$/)]
        ],
        cvv: ['', [Validators.required, Validators.pattern(/^\d{3}$/)]],

        country: ['Morocco', Validators.required],
        firstName: [''],
        lastName: [''],
        address: ['', Validators.required],
        postalCode: ['', Validators.required],
        city: ['', Validators.required],
        province: ['', Validators.required],
        phone: ['', Validators.required]
      });
    }

    get f() {
      return this.form.controls;
    }

    get subtotal(): number {
      return this.cartItems.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
      );
    }

    get shippingFee(): string {
      return 'Calculated at next step';
    }

    get total(): number {
      const shippingCost = this.discountApplied ? 0 : 15;
      return Math.round((this.subtotal + shippingCost) * 100) / 100;
    }


    applyDiscount(): void {
      this.paymentService.applyDiscount(this.discountCode, this.cartItems[0].orderId)
        .subscribe();
    }

    placeOrder(): void {
      if (this.form.invalid) {
        this.form.markAllAsTouched();
        return;
      }

      const payload = {
        contact: {
          email: this.f.email.value,
          saveInfo: this.f.saveInfo.value
        },
        payment: {
          cardNumber: this.f.cardNumber.value,
          expiry: this.f.expiry.value,
          cvv: this.f.cvv.value
        },
        shipping: {
          country: this.f.country.value,
          firstName: this.f.firstName.value,
          lastName: this.f.lastName.value,
          address: this.f.address.value,
          postalCode: this.f.postalCode.value,
          city: this.f.city.value,
          province: this.f.province.value,
          phone: this.f.phone.value
        },
        cart: this.cartItems,
        total: this.total
      };

      console.debug('Order payload:', payload);
      alert('Your order has been placed!');

      this.form.reset({
        country: 'Morocco',
        saveInfo: false,
      });
    }
}
