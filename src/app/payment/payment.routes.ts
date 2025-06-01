import { Routes } from "@angular/router";

export const paymentRoutes: Routes = [

  {
    path: 'checkout',
    loadComponent: () =>
      import('./checkout/checkout.component').then(m => m.CheckoutComponent)
  },
  {
    path: 'basket',
    loadComponent: () =>
      import('./basket/basket.component').then(m => m.BasketComponent),
  },
];
