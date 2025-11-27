

import { Component, OnInit } from "@angular/core";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import { NzMessageService } from "ng-zorro-antd/message";
import { CustomerService } from "../../service/customer.service";


@Component({
  selector: 'app-view-bookings',
  templateUrl: './view-bookings.component.html',
  styleUrls: ['./view-bookings.component.scss']
})
export class ViewBookingsComponent implements OnInit {
  currentPage: number = 1;
  total: number = 0;
  bookings: any[] = [];
  pageSize: number = 2; // Show 2 rooms per page

  constructor(private customerService: CustomerService, private message: NzMessageService) {}

  ngOnInit() {
    this.loadBookings();
  }

  loadBookings() {
    this.customerService.getMyBookings(this.currentPage - 1, this.pageSize).subscribe(res => {
      this.bookings = res.reservationDtoList.map((b:any) => ({
        ...b,
        reservationStatus: b.reservationStatus?.toUpperCase() || 'PENDING',
        paymentStatus: b.paymentStatus?.toUpperCase() || (b.reservationStatus==='APPROVED'?'WAIT':'NOT_AVAILABLE')
      }));
      this.total = res.totalPages * this.pageSize;
    });
  }

  pageIndexChange(page: number) {
    this.currentPage = page;
    this.loadBookings();
  }

  pay(reservationId:number) {
    this.customerService.payReservation(reservationId).subscribe({
      next: () => { this.message.success('Payment successful'); this.loadBookings(); },
      error: err => { this.message.error(err.error || 'Payment failed'); }
    });
  }

  downloadReceipt(reservation: any) {
  const doc = new jsPDF();

  // Title
  doc.setFont('helvetica', 'bold');
  doc.setFontSize(16);
  doc.text(`Booking Receipt for ${reservation.username}`, 10, 20);

  // Table
  autoTable(doc, {
    startY: 30,
    head: [['Field', 'Value']],
    body: [
      ['Reservation ID', reservation.id],
      ['Customer Name', reservation.username],
      ['Room', reservation.roomName],
      ['Room Type', reservation.roomType],
      ['Price', reservation.price],
      ['Reservation Status', reservation.reservationStatus],
      ['Payment Status', reservation.paymentStatus],
      ['Transaction ID', reservation.transactionId || 'N/A'],
      ['Payment Date', reservation.paymentDate || 'N/A']
    ],
    headStyles: { fillColor: [41, 128, 185], textColor: 255 }, // Blue header
    bodyStyles: { textColor: 0 }, // Black text for body
    theme: 'grid'
  });

  // Styled thank-you message
  const finalY = (doc as any).lastAutoTable.finalY || 50;
  doc.setFontSize(12);
  doc.setFont('helvetica', 'bold');
  doc.setTextColor(39, 174, 96); // Green color
  doc.text("Thank you for booking! Enjoy our service and visit again.", 105, finalY + 15, { align: 'center' });

  // Save PDF
  doc.save(`Receipt_${reservation.username}.pdf`);
}

}

