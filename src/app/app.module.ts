import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {MyApp} from './app.component';
import {PreferencesPage} from './pages/preferences/preferences.component';
import {ContactPage} from './pages/contact/contact.component';
import {HomePage} from './pages/home/home.component';
import {TabsPage} from './pages/tabs/tabs.component';
import {ItemDetailsPage} from './pages/item-detail/item-detail.component';
import {ItemsList} from './items/items-list/items-list.component';
import {ItemsService} from './shared/services/items.service';
import {CategoriesService} from './shared/services/categories.service';
import {CategoriesPipe} from "./shared/pipes/items-categories.pipe";
import {ProgressBarComponent} from "./shared/progress-bar/progress-bar.component";
import {HttpModule, Http} from "@angular/http";
import {TranslateModule, TranslateLoader} from "ng2-translate";
import {TranslateLoaderFactory} from './app.translate.factory';
import {CategoryDetailsPage} from "./pages/category-detail/category-detail.component";
import {Storage} from '@ionic/storage';
import {AddElementPage} from "./pages/add-element/add-element.component";

export function provideStorage() {
  return new Storage(['sqlite', 'websql', 'indexeddb'], {name: '__mydb'});
}

@NgModule({
  declarations: [
    MyApp,
    PreferencesPage,
    ContactPage,
    HomePage,
    TabsPage,
    AddElementPage,
    CategoryDetailsPage,
    ItemDetailsPage,
    ItemsList,
    CategoriesPipe,
    ProgressBarComponent
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    HttpModule,
    TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: TranslateLoaderFactory,
      deps: [Http]
    })
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    PreferencesPage,
    ContactPage,
    HomePage,
    TabsPage,
    AddElementPage,
    CategoryDetailsPage,
    ItemDetailsPage,
    ItemsList
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler}, ItemsService, CategoriesService, {
    provide: Storage,
    useFactory: provideStorage
  }]
})

export class AppModule {
}
