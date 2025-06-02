export interface DiscountCartItem {
  items: [
      {
      productName: string;
      price: number;
      quantity: number;
      isDiscounted: boolean;
    }
  ]
}
