import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Report } from 'src/app/model/Report';

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.css']
})
export class ReportDialogComponent {
  report?: Report
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig){
    this.report = config.data
  }


  formGroup = new FormGroup({})

  onSubmit() {}
}
