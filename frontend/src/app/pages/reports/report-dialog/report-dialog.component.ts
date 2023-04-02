import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Report } from 'src/app/model/Report';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.css']
})
export class ReportDialogComponent {
  report?: Report
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private reportService: ReportService){
    this.report = config.data
  }

  formGroup = new FormGroup({})

  onSubmit() {}

  resolve() {
    this.reportService.resolveReport(Number(this.report?.id)).subscribe({
      next: (data: any) => {
        console.log(data)
      },
      error: (e) => console.error(e),
      complete: () => console.info('login complete') 
    });
  }
}
