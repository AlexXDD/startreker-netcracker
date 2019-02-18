import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipePipe implements PipeTransform {

  transform(object: any[], fieldName: string, searchStr: string): any {

    return object.filter(obj => obj[fieldName].toLowerCase().indexOf(searchStr.toLowerCase()) !== -1);
  }
}
