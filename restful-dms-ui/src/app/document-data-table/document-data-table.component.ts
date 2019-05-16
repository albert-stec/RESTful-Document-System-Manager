import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator, MatSort} from '@angular/material';
import {DocumentDataTableDataSource} from './document-data-table-datasource';

@Component({
  selector: 'app-document-data-table',
  templateUrl: './document-data-table.component.html',
  styleUrls: ['./document-data-table.component.css']
})
export class DocumentDataTableComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  dataSource: DocumentDataTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'name'];

  ngAfterViewInit() {
    this.dataSource = new DocumentDataTableDataSource(this.paginator, this.sort);
  }
}
