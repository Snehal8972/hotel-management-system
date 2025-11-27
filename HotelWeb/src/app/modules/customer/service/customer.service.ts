
 import { HttpClient, HttpHeaders } from '@angular/common/http';
 import { Injectable } from '@angular/core';
 import { Observable } from 'rxjs'; import { UserStorageService } from '../../../auth/services/user-storage.service';

const BASE_URL = 'http://localhost:8080/api/customer/';

 @Injectable({
  providedIn: 'root'
 })
export class CustomerService {

   constructor(private http: HttpClient) { }

   private createAuthorizationHeader() {
     return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + UserStorageService.getToken()
    });
  }

  // Fetch rooms with pagination
  getRooms(pageNumber: number): Observable<any> {
    return this.http.get(`${BASE_URL}rooms/${pageNumber}`, {
      headers: this.createAuthorizationHeader()
    });
  }

  // Book a room
  bookRooms(bookingDto: any): Observable<any> {
    return this.http.post(`${BASE_URL}book`, bookingDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  // Fetch my bookings
  getMyBookings(pageNumber: number, pageSize: number): Observable<any> {
    const userId = UserStorageService.getUserId();
    return this.http.get(`${BASE_URL}bookings/${userId}/${pageNumber}`, {
      headers: this.createAuthorizationHeader()
    });
  }

  // Update a booking
  updateReservation(id: number, reservationDto: any): Observable<any> {
    return this.http.put(`${BASE_URL}booking/${id}`, reservationDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  // Pay for reservation
  payReservation(id: number): Observable<any> {
    return this.http.post(`${BASE_URL}reservation/pay/${id}`, {}, {
      headers: this.createAuthorizationHeader()
    });
  }
}




















