import {Injectable, Pipe, PipeTransform} from "@angular/core";
import {TranslateService} from "ng2-translate";

@Pipe({
  name: 'orderAlphabetically',
  pure: false
})

@Injectable()
export class OrderAlphabeticallyPipe implements PipeTransform {

  constructor(private translateService: TranslateService) {
  }

  transform(elements: any): any[] {
    if (!elements) return [];

    let translations = [];
    for (let i = 0; i < elements.length; i++) {
      translations.push(elements[i].id);
    }

    this.translateService.get(translations, {}).subscribe((texts) => {
      for (let i = 0; i < elements.length; i++) {
        elements[i].translation = texts[elements[i].id];
      }
    });

    return elements.sort(function(a, b){
      if(a.translation < b.translation) return -1;
      if(a.translation > b.translation) return 1;
      return 0;
    });
  }
}
