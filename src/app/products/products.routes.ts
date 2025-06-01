import { Routes } from "@angular/router";

export const productsRoutes: Routes = [
  {
    path: '',
    redirectTo: 'listing',
    pathMatch: 'full'
  },
  {
    path: 'listing',
    loadComponent: () =>
      import('./listing/listing.component').then(m => m.ListingComponent)
  },
  {
    path: ':id',
    loadComponent: () =>
      import('./product/product.component').then(m => m.ProductComponent)
  },
];
