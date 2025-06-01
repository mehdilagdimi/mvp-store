import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'payment',
    pathMatch: 'full'
  },
  {
    path: 'products',
    loadChildren: () => import('./products/products.routes')
      .then((m) => m.productsRoutes)
  },
  {
    path: 'order',
    loadChildren: () => import('./order/order.routes')
      .then((m) => m.orderRoutes)
  },
  {
    path: 'payment',
    loadChildren: () => import('./payment/payment.routes')
      .then((m) => m.paymentRoutes)
  }
];
