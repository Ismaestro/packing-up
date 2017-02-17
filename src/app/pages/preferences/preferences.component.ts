import {Component} from '@angular/core';
import {Storage} from '@ionic/storage';
import { AppRate } from 'ionic-native';
import {TranslateService} from "ng2-translate";
import {ItemsService} from "../../shared/services/items.service";
import {CategoriesService} from "../../shared/services/categories.service";
import {AlertController} from "ionic-angular";

@Component({
  selector: 'page-preferences',
  templateUrl: 'preferences.component.html'
})

export class PreferencesPage {

  private translateService: TranslateService;
  private texts: Array<string>;

  constructor(translateService: TranslateService,
              private categoriesService: CategoriesService,
              private itemsService: ItemsService,
              private storage: Storage,
              public alertCtrl: AlertController) {
    this.translateService = translateService;
    this.translateService.get(['areYouSure', 'resetListInfo', 'accept', 'cancel'],{}).subscribe((texts) => {
      this.texts = texts;
    });

    AppRate.preferences = {
      openStoreInApp: true,
      displayAppName: 'Packing up',
      usesUntilPrompt: 2,
      promptAgainForEachNewVersion: true,
      storeAppURL: {
        android: 'market://details?id=packingup.core.activities',
      },
      useLanguage: this.translateService.getBrowserLang()
    };

  }

  setLanguage(language: string): void {
    this.translateService.use(language);
  }

  showConfirm() {
    let confirm = this.alertCtrl.create({
      title: this.texts['areYouSure'],
      message: this.texts['resetListInfo'],
      buttons: [
        {
          text: this.texts['cancel'],
          handler: () => {
          }
        },
        {
          text: this.texts['accept'],
          handler: () => {
            this.resetDB();
          }
        }
      ]
    });
    confirm.present();
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

  rateApp(){
    AppRate.promptForRating(true);
  }

}
