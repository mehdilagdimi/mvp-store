import { Routes } from "@angular/router";

export const orderRoutes: Routes = [

  {
    path: 'basket',
    loadComponent: () =>
      import('./basket/basket.component').then(m => m.BasketComponent)
  }
];
