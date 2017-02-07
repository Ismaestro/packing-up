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
  public categories = ['A','B'];
  public itemsChecked = 0;

  constructor(private itemsService: ItemsService,
              private nav: NavController,
              private platform: Platform,
              private zone: NgZone,
              private modalCtrl: ModalController) {
    this.platform.ready().then(() => {

      this.itemsService.loadInitData().then(() => {
        this.itemsService.getAll()
          .then(data => {
            this.zone.run(() => {
              this.items = data;
              this.calculateItemsChecked();
            });
          });
      });
    });
  }

  itemChanged(item) {
    this.itemsService.updateItem(item);
    this.calculateItemsChecked(item);
  }

  showDetail(item) {
    let modal = this.modalCtrl.create(DetailsPage, {item: item});
    modal.present();
  }

  calculateItemsChecked(item?) {
    let itemCopy = Object.assign({}, item);
    let counter = 0;
    for (let item of this.items) {
      if (itemCopy._id === item._id) {
        item.checked = itemCopy.checked; // wtf
      }
      if (item.checked === true) {
        counter++;
      }
    }

    this.itemsChecked = counter;
  }
}
