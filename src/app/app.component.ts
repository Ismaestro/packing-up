import {Component} from '@angular/core';
import {Platform} from 'ionic-angular';
import {StatusBar, Splashscreen} from 'ionic-native';

import {TabsPage} from './pages/tabs/tabs.component';

@Component({
  templateUrl: 'app.component.html'
})

export class MyApp {
  rootPage = TabsPage;

  constructor(platform: Platform) {
    platform.ready().then(() => {
      StatusBar.styleDefault();
      Splashscreen.hide();
    });
  }
}
