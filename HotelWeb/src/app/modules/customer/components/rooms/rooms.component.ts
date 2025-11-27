import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { UserStorageService } from '../../../../auth/services/user-storage.service';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent {
  currentPage = 1;
  rooms: any[] = [];
  totalPages = 0;
  total: number = 0;
  loading = false;

  isVisibleMiddle = false;
  date: Date[] = [];
  checkInDate: Date;
  checkOutDate: Date;
  selectedRoomId: number;

  constructor(
    private customerService: CustomerService,
    private message: NzMessageService
  ) {
    this.getRooms();
  }

  // Fetch rooms with pagination
  getRooms() {
    this.loading = true;
    this.customerService.getRooms(this.currentPage - 1).subscribe({
      next: res => {
        this.rooms = res.roomDtoList;
        this.totalPages = res.totalPages;
        this.total = res.totalPages;
        this.loading = false;
      },
      error: err => {
        this.loading = false;
        this.message.error('Failed to load rooms!');
      }
    });
  }

  pageIndexChange(page: number) {
    this.currentPage = page;
    this.getRooms();
  }

  // Show modal for booking
  showModalMiddle(roomId: number) {
    this.selectedRoomId = roomId;
    this.isVisibleMiddle = true;
  }

  // Handle date selection
  onChange(result: Date[]) {
    if (result.length === 2) {
      this.checkInDate = result[0];
      this.checkOutDate = result[1];
    }
  }

  handleCancleMiddle() {
    this.isVisibleMiddle = false;
  }

  handleOkMiddle(): void {
    if (!this.checkInDate || !this.checkOutDate) {
      this.message.warning('Please select check-in and check-out dates!');
      return;
    }

    // Convert dates to ISO string to send to backend
    const bookingDto = {
      userId: UserStorageService.getUserId(),
      roomId: this.selectedRoomId,
      checkInDate: this.checkInDate.toISOString(),
      checkOutDate: this.checkOutDate.toISOString()
    };

    this.customerService.bookRooms(bookingDto).subscribe({
      next: res => {
        this.message.success('Booking request submitted for approval!', { nzDuration: 5000 });
        this.isVisibleMiddle = false;
      },
      error: err => {
        console.error(err);
        if (err.error && typeof err.error === 'string') {
          this.message.error(err.error, { nzDuration: 5000 });
        } else {
          this.message.error('Something went wrong! Please try again.', { nzDuration: 5000 });
        }
      }
    });
  }
}







