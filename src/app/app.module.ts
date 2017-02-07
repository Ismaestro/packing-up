import {NgModule, ErrorHandler} from '@angular/core';
import {IonicApp, IonicModule, IonicErrorHandler} from 'ionic-angular';
import {MyApp} from './app.component';
import {PreferencesPage} from './pages/preferences/preferences.component';
import {ContactPage} from './pages/contact/contact.component';
import {HomePage} from './pages/home/home.component';
import {TabsPage} from './pages/tabs/tabs.component';
import {DetailsPage} from './pages/item-detail/item-detail.component';
import {ItemsList} from './items/items-list/items-list.component';
import {ItemsService} from './items/items-list/items-list.service';
import {CategoriesPipe} from "./shared/pipes/items-categories.pipe";
import {ProgressBarComponent} from "./shared/progress-bar/progress-bar.component";
import {HttpModule, Http} from "@angular/http";
import {TranslateModule, TranslateLoader} from "ng2-translate";
import {TranslateLoaderFactory} from './app.translate.factory';

@NgModule({
  declarations: [
    MyApp,
    PreferencesPage,
    ContactPage,
    HomePage,
    TabsPage,
    DetailsPage,
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
    DetailsPage,
    ItemsList
  ],
  providers: [{provide: ErrorHandler, useClass: IonicErrorHandler}, ItemsService]
})

export class AppModule {
}
