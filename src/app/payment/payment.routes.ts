import { Routes } from "@angular/router";

export const paymentRoutes: Routes = [

  {
    path: '',
    redirectTo: 'checkout',
    pathMatch: 'full'
  },
  {
    path: 'checkout',
    loadComponent: () =>
      import('./checkout/checkout.component').then(m => m.CheckoutComponent)
  }
];
