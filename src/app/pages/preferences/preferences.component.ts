import {Component} from '@angular/core';
import {Storage} from '@ionic/storage';
import {Market} from 'ionic-native';
import {TranslateService} from "ng2-translate";
import {ItemsService} from "../../shared/services/items.service";
import {CategoriesService} from "../../shared/services/categories.service";
import {AlertController} from "ionic-angular";

@Component({
  selector: 'page-preferences',
  templateUrl: 'preferences.component.html'
})

export class PreferencesPage {

  private language: string;
  private translateService: TranslateService;

  constructor(translateService: TranslateService,
              private categoriesService: CategoriesService,
              private itemsService: ItemsService,
              private storage: Storage,
              public alertCtrl: AlertController) {
    this.translateService = translateService;
    this.language = this.translateService.currentLang;
  }

  rateApp() {
    Market.open('com.ismaestro.packingup');
  }

  setLanguage(language: string): void {
    this.translateService.use(language);
    this.storage.set('language', language);
  }

  showConfirm() {
    this.translateService.get(['areYouSure', 'resetListInfo', 'accept', 'cancel'], {}).subscribe((texts) => {
      let confirm = this.alertCtrl.create({
        title: texts['areYouSure'],
        message: texts['resetListInfo'],
        buttons: [
          {
            text: texts['cancel'],
            handler: () => {
            }
          },
          {
            text: texts['accept'],
            handler: () => {
              this.resetDB();
            }
          }
        ]
      });
      confirm.present();
    });
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
