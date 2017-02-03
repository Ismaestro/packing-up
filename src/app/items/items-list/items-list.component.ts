import {Component, NgZone} from "@angular/core";
import {ModalController, NavController, Platform} from 'ionic-angular';
import {ItemsService} from './items-list.service';
import {DetailsPage} from '../../pages/item-detail/item-detail.component';

@Component({
  selector: 'items-list',
  templateUrl: 'items-list.component.html'
})
export class ItemsList {
  public items = [];

  constructor(private itemsService: ItemsService,
              private nav: NavController,
              private platform: Platform,
              private zone: NgZone,
              private modalCtrl: ModalController) {
    this.platform.ready().then(() => {
      this.itemsService.initDB();

      this.itemsService.getAll()
        .then(data => {
          this.zone.run(() => {
            this.items = data;
          });
        })
        .catch(console.error.bind(console));
    });
  }

  showDetail(item) {
    let modal = this.modalCtrl.create(DetailsPage, {item: item});
    modal.present();
  }
}
