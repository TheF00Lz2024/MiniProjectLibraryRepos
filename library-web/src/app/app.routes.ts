import { RouterModule, Routes } from '@angular/router';
import { ViewAllBookComponent } from './view-all-book/view-all-book.component';
import { ViewAllUserComponent } from './view-all-user/view-all-user.component';
import { AddUserComponent } from './add-user/add-user.component';
import { CreateBookComponent } from './create-book/create-book.component';

export const routes: Routes = [
    {path: 'view-all-book', component: ViewAllBookComponent},
    {path: 'view-all-user', component: ViewAllUserComponent},
    {path: 'create-book', component: CreateBookComponent},
    {path: 'add-user', component: AddUserComponent},
];