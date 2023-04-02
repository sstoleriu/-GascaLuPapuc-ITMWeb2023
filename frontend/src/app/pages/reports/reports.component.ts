import { Component } from '@angular/core';
import { Report } from 'src/app/model/Report';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { ReportDialogComponent } from './report-dialog/report-dialog.component';
import { ReportService } from 'src/app/services/report.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
  providers: [DialogService, MessageService]
})
export class ReportsComponent {
  constructor(public dialogService: DialogService, public messageService: MessageService, private reportService: ReportService, private userService: UserService){}
  items = [
      {
          label: 'My Reports',
          icon: 'pi pi-fw pi-envelope',

      },
      {
        label: 'Manage operators',
        icon: 'pi pi-fw pi-users',
      },
      {
        label: 'LogOut',
        icon: 'pi pi-fw pi-sign-out',
        class: 'text-red'
      }
  ];

  tabMenuItems =  [
    {
        label: 'My Reports',
        icon: 'pi pi-fw pi-envelope',

    },
    {
      label: 'Spam Reports',
      icon: 'pi pi-fw pi-exclamation-triangle',
    }
  ];

  activeItem = this.tabMenuItems[0]

  raports: Report[] = [
    /*{
      id: 1,
      listCategoryOfObject: ["poate", "nu stiu"],
      description: "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor ",
      listOfCharacteristics: ["aoleu", "da"],
      createDate: "MARE",
      resolveDate: "20-10-2022",
      isResolve: true,
      isAnon: true
    },
    {
      id: 2,
      listCategoryOfObject: ["da", "nu"],
      description: "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor ",
      listOfCharacteristics: ["aoleu", "da"],
      createDate: "MARE",
      resolveDate: "20-10-2022",
      isResolve: false,
      isAnon: false
    }*/
  ]

  statuses = [
    { label: 'New', value: 'new' },
    { label: 'Processed', value: 'processed' },
    { label: 'Spam', value: 'spam' }
  ];

  value = '';

  ref?: DynamicDialogRef;

  getSeverity(status: boolean) {
    switch (status) {
        case false:
            return 'danger';

        case true:
            return 'success';
    }

    return 'success';
  }

  options = {
    center: {lat: 36.890257, lng: 30.707417},
    zoom: 12
  };

  ngOnInit() {
    var userId = Number(this.userService.jwtPayload?.id);

    this.reportService.getReports(userId).subscribe({
      next: (data: Report[]) => {
        console.log(data)
        this.raports = data;
      },
      error: (e) => console.error(e),
      complete: () => console.info('login complete') 
    });
  }

  openReportDialog(raport: Report) {
    this.ref = this.dialogService.open(ReportDialogComponent, {
      data: raport,
      header: "View Report",
      width: '70%',
      contentStyle: { overflow: 'auto' },
    })
  }
}
