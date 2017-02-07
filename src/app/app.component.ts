import {Component} from '@angular/core';
import {Platform} from 'ionic-angular';
import {StatusBar, Splashscreen} from 'ionic-native';

import {TabsPage} from './pages/tabs/tabs.component';
import {TranslateService} from "ng2-translate";

@Component({
  templateUrl: 'app.component.html'
})

export class MyApp {
  rootPage = TabsPage;
  private translateService: TranslateService;

  constructor(platform: Platform, translateService: TranslateService) {
    this.translateService = translateService;
    this.translateService.setDefaultLang('en');
    this.translateService.use(this.translateService.getBrowserLang());

    platform.ready().then(() => {
      StatusBar.styleDefault();
      Splashscreen.hide();
    });
  }


}
