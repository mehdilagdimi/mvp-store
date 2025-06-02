export interface CartItem {
  id: string;
  orderId: string,
  name: string;
  price: number;
  discountedPrice?: number;
  quantity: number;
  imageUrl: string;
}
