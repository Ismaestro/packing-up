import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {MyApp} from './app.component';
import {AboutPage} from './pages/about/about.component';
import {ContactPage} from './pages/contact/contact.component';
import {HomePage} from './pages/home/home.component';
import {TabsPage} from './pages/tabs/tabs.component';
import {DetailsPage} from './pages/item-detail/item-detail.component';
import {ItemsList} from './items/items-list/items-list.component';
import {ItemsService} from './items/items-list/items-list.service';
import {CategoriesPipe} from "./shared/pipes/items-categories.pipe";

@NgModule({
  declarations: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    TabsPage,
    DetailsPage,
    ItemsList,
    CategoriesPipe
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    TabsPage,
    DetailsPage,
    ItemsList
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler}, ItemsService]
})

export class AppModule {
}
