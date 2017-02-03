import {Component} from '@angular/core';

import {HomePage} from '../home/home.component';
import {AboutPage} from '../about/about.component';
import {ContactPage} from '../contact/contact.component';

@Component({
  templateUrl: 'tabs.component.html'
})

export class TabsPage {
  tab1Root: any = HomePage;
  tab2Root: any = AboutPage;
  tab3Root: any = ContactPage;

  constructor() {

  }
}
