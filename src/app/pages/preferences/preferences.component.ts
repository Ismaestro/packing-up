import {Component} from '@angular/core';

import {TranslateService} from "ng2-translate";

@Component({
  selector: 'page-about',
  templateUrl: 'preferences.component.html'
})

export class AboutPage {

  private translateService: TranslateService;

  constructor(translateService: TranslateService) {
    this.translateService = translateService;
  }

  setLanguage(language: string): void {
    this.translateService.use(language);
  }

}
