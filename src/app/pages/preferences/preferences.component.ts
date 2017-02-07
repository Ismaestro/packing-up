import {Component} from '@angular/core';
import {TranslateService} from "ng2-translate";
import {ItemsService} from "../../items/items-list/items-list.service";

@Component({
  selector: 'page-about',
  templateUrl: 'preferences.component.html'
})

export class AboutPage {

  private translateService: TranslateService;

  constructor(translateService: TranslateService,
  private itemsService: ItemsService) {
    this.translateService = translateService;
  }

  setLanguage(language: string): void {
    this.translateService.use(language);
  }

  resetDB(): void {
    this.itemsService.resetDB();
  }

}
