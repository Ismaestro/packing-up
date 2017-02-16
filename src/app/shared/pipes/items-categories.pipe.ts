import {Injectable, Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'filterCategory',
  pure: false
})

@Injectable()
export class CategoriesPipe implements PipeTransform {

  transform(items: any[], field: string, value: string): any[] {
    if (!items) return [];

    items = items.filter(it => it[field] == value);

    return items.sort(function(a, b){
      if(a.translation < b.translation) return -1;
      if(a.translation > b.translation) return 1;
      return 0;
    });
  }
}
