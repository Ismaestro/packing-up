import {Component} from '@angular/core';

import {HomePage} from '../home/home.component';
import {PreferencesPage} from '../preferences/preferences.component';
import {ContactPage} from '../contact/contact.component';

@Component({
  templateUrl: 'tabs.component.html'
})

export class TabsPage {
  tab1Root: any = HomePage;
  tab2Root: any = PreferencesPage;
  tab3Root: any = ContactPage;

  constructor() {

  }
}
