import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Report } from '../model/Report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  getReports(id: number) {
    return this.http.get<Report[]>('http://192.168.1.149:8082/api/v1/report/allReports/user/' + id);
  }
}
