import {Injectable, Pipe, PipeTransform} from "@angular/core";
import {TranslateService} from "ng2-translate";

@Pipe({
  name: 'filter',
  pure: false
})

@Injectable()
export class CategoriesPipe implements PipeTransform {

  constructor(private translateService: TranslateService) {
  }

  transform(items: any[], field: string, value: string): any[] {
    if (!items) return [];

    items = items.filter(it => it[field] == value);

    let translations = [];
    for (let i = 0; i < items.length; i++) {
      translations.push(items[i].id);
    }

    this.translateService.get(translations, {}).subscribe((texts) => {
      for (let i = 0; i < items.length; i++) {
        items[i].translation = texts[items[i].id];
      }
    });

    return items.sort(function(a, b){
      if(a.translation < b.translation) return -1;
      if(a.translation > b.translation) return 1;
      return 0;
    });
  }
}
