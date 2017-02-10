import {Component} from '@angular/core';
import {Platform} from 'ionic-angular';
import {StatusBar, Splashscreen} from 'ionic-native';
import {Storage} from '@ionic/storage';

import {TabsPage} from './pages/tabs/tabs.component';
import {TranslateService} from "ng2-translate";

@Component({
  templateUrl: 'app.component.html'
})

export class MyApp {
  rootPage = TabsPage;

  constructor(platform: Platform, private translateService: TranslateService,
              private storage: Storage) {
    this.translateService = translateService;
    this.translateService.setDefaultLang('en');
    this.translateService.use(this.translateService.getBrowserLang());

    storage.get('storageLoaded').then((storageLoaded) => {
      if (!storageLoaded) {
        this.loadInitData().then(() => {
          storage.set('storageLoaded', 'true').then(() => {
            location.reload();
          });
        })
      }
    });

    platform.ready().then(() => {
      StatusBar.styleDefault();
      Splashscreen.hide();
    });
  }

  loadInitData() {
    return this.loadCategories().then(() => {
      return this.loadItems();
    });
  }

  loadCategories() {
    let categories = [
      {id: 'documentation'},
      {id: 'clothes'},
      {id: 'dressing_case'},
      {id: 'media'},
      {id: 'mountain'},
      {id: 'beach'},
      {id: 'work'},
      {id: 'kit'},
      {id: 'other'}
    ];

    return this.storage.set('categories', categories);
  }

  loadItems() {
    let items = [
      {id: "passport", categoryId: 'documentation'},
      {id: "driver_license", categoryId: 'documentation'},
      {id: "credit_cards", categoryId: 'documentation'},
      {id: "tickets", categoryId: 'documentation'},
      {id: "skirts", categoryId: 'clothes'},
      {id: "jewelry", categoryId: 'clothes'},
      {id: "wristwatch", categoryId: 'clothes'},
      {id: "glasses", categoryId: 'clothes'},
      {id: "footwear", categoryId: 'clothes'},
      {id: "socks", categoryId: 'clothes'},
      {id: "shorts", categoryId: 'clothes'},
      {id: "trousers", categoryId: 'clothes'},
      {id: "underwear", categoryId: 'clothes'},
      {id: "t_shirts", categoryId: 'clothes'},
      {id: "cap", categoryId: 'clothes'},
      {id: "hat", categoryId: 'clothes'},
      {id: "rain_coat", categoryId: 'clothes'},
      {id: "scarf", categoryId: 'clothes'},
      {id: "gloves", categoryId: 'clothes'},
      {id: "night_suit", categoryId: 'clothes'},
      {id: "slippers", categoryId: 'clothes'},
      {id: "scarves", categoryId: 'clothes'},
      {id: "makeup", categoryId: 'clothes'},
      {id: "lipstick", categoryId: 'clothes'},
      {id: "tampons", categoryId: 'dressing_case'},
      {id: "salvaslip", categoryId: 'dressing_case'},
      {id: "cleansing_wipes", categoryId: 'dressing_case'},
      {id: "moisturizer", categoryId: 'dressing_case'},
      {id: "foam_hair", categoryId: 'dressing_case'},
      {id: "conditioner", categoryId: 'dressing_case'},
      {id: "gums", categoryId: 'dressing_case'},
      {id: "nail_polish", categoryId: 'dressing_case'},
      {id: "nail_file", categoryId: 'dressing_case'},
      {id: "perfume", categoryId: 'dressing_case'},
      {id: "bath_gel", categoryId: 'dressing_case'},
      {id: "shampoo", categoryId: 'dressing_case'},
      {id: "sponge", categoryId: 'dressing_case'},
      {id: "dryer", categoryId: 'dressing_case'},
      {id: "comb", categoryId: 'dressing_case'},
      {id: "deodorant", categoryId: 'dressing_case'},
      {id: "toothbrush", categoryId: 'dressing_case'},
      {id: "toothpaste", categoryId: 'dressing_case'},
      {id: "colony", categoryId: 'dressing_case'},
      {id: "gomina", categoryId: 'dressing_case'},
      {id: "creams", categoryId: 'dressing_case'},
      {id: "ear_buds", categoryId: 'dressing_case'},
      {id: "shaving_gel", categoryId: 'dressing_case'},
      {id: "safety_razor", categoryId: 'dressing_case'},
      {id: "nail_clippers", categoryId: 'dressing_case'},
      {id: "map", categoryId: 'mountain'},
      {id: "compass", categoryId: 'mountain'},
      {id: "waist", categoryId: 'mountain'},
      {id: "lantern", categoryId: 'mountain'},
      {id: "binoculars", categoryId: 'mountain'},
      {id: "rope", categoryId: 'mountain'},
      {id: "whistle", categoryId: 'mountain'},
      {id: "food_tools", categoryId: 'mountain'},
      {id: "pencil_and_paper", categoryId: 'mountain'},
      {id: "sleeping_bag", categoryId: 'mountain'},
      {id: "swiss_knife", categoryId: 'mountain'},
      {id: "lighter", categoryId: 'mountain'},
      {id: "thermos", categoryId: 'mountain'},
      {id: "pillow", categoryId: 'mountain'},
      {id: "swimsuit", categoryId: 'beach'},
      {id: "sunscreen", categoryId: 'beach'},
      {id: "after_sun", categoryId: 'beach'},
      {id: "thongs", categoryId: 'beach'},
      {id: "towel", categoryId: 'beach'},
      {id: "parasol", categoryId: 'beach'},
      {id: "mats", categoryId: 'beach'},
      {id: "racquets", categoryId: 'beach'},
      {id: "cards", categoryId: 'beach'},
      {id: "camera", categoryId: 'media'},
      {id: "charger", categoryId: 'media'},
      {id: "mobile", categoryId: 'media'},
      {id: "ipod", categoryId: 'media'},
      {id: "headphones", categoryId: 'media'},
      {id: "radio", categoryId: 'media'},
      {id: "GPS", categoryId: 'media'},
      {id: "tablet", categoryId: 'media'},
      {id: "memory_card", categoryId: 'media'},
      {id: "ties", categoryId: 'work'},
      {id: "breath_mints", categoryId: 'work'},
      {id: "pants", categoryId: 'work'},
      {id: "skirt_suit", categoryId: 'work'},
      {id: "jacket", categoryId: 'work'},
      {id: "shirts", categoryId: 'work'},
      {id: "cufflink", categoryId: 'work'},
      {id: "shoes", categoryId: 'work'},
      {id: "phone", categoryId: 'work'},
      {id: "laptop", categoryId: 'work'},
      {id: "flash_drive", categoryId: 'work'},
      {id: "work_material", categoryId: 'work'},
      {id: "band_aids", categoryId: 'kit'},
      {id: "scissors", categoryId: 'kit'},
      {id: "paracetamol", categoryId: 'kit'},
      {id: "sticking_plaster", categoryId: 'kit'},
      {id: "thermometer", categoryId: 'kit'},
      {id: "antiseptic", categoryId: 'kit'},
      {id: "keys", categoryId: 'other'},
      {id: "cash", categoryId: 'other'},
      {id: "umbrella", categoryId: 'other'},
      {id: "voltage_converter", categoryId: 'other'},
      {id: "anti_mosquito", categoryId: 'other'},
      {id: "travel_locks", categoryId: 'other'},
      {id: "plastic_bags", categoryId: 'other'},
      {id: "condoms", categoryId: 'other'},
      {id: "toys", categoryId: 'other'},
      {id: "baby_food", categoryId: 'other'},
      {id: "book", categoryId: 'other'},
      {id: "snacks", categoryId: 'other'},
      {id: "shoes_insoles", categoryId: 'other'},
      {id: "water", categoryId: 'other'},
      {id: "travel_pillow", categoryId: 'other'}
    ];

    return this.storage.set('items', items);
  }

}
