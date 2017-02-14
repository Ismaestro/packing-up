import {Component} from '@angular/core';
import {Storage} from '@ionic/storage';
import {TranslateService} from "ng2-translate";
import {ItemsService} from "../../shared/services/items.service";
import {CategoriesService} from "../../shared/services/categories.service";

@Component({
  selector: 'page-preferences',
  templateUrl: 'preferences.component.html'
})

export class PreferencesPage {

  private translateService: TranslateService;

  constructor(translateService: TranslateService,
              private categoriesService: CategoriesService,
              private itemsService: ItemsService,
              private storage: Storage) {
    this.translateService = translateService;
  }

  setLanguage(language: string): void {
    this.translateService.use(language);
  }

  resetDB(): void {
    this.storage.remove('storageLoaded').then(() => {
      this.itemsService.removeAll().then(() => {
        this.categoriesService.removeAll().then(() => {
          location.reload();
        });
      });
    });
  }

}
