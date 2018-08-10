import { PlateDetailsService } from './plate-details.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';
  carParkingStatus: Boolean = false;
  bikeParkingStatus: Boolean = false;
  bikeStatusText: String;
  carStatusText: String;
  constructor(private plateDetailsService: PlateDetailsService) { }

  ngOnInit() {
    // this.checkParkingStatus();
    // this.plateDetailsService.getParkingFullStatus().subscribe(response => {
    //   this.carParkingStatus = response.carParking;
    //   this.bikeParkingStatus = response.bikeParking;
    //   this.carStatusText = this.carParkingStatus ? "Car Parking Full" : "Car Parking Available";
    //   this.bikeStatusText = this.bikeParkingStatus ? "Bike Parking Full" : "Bike Parking Available";
    // },
    //   error => {
    //     console.log(error);
    //   });
  }

  // checkParkingStatus() {
  //   let ref = this;
  //   setInterval(() => {
  //     ref.plateDetailsService.getParkingFullStatus().subscribe(response => {
  //       ref.carParkingStatus = response.carParking;
  //       ref.bikeParkingStatus = response.bikeParking;
  //       ref.carStatusText = ref.carParkingStatus ? "Car Parking Full" : "Car Parking Available";
  //       ref.bikeStatusText = ref.bikeParkingStatus ? "Bike Parking Full" : "Bike Parking Available";
  //     },
  //       error => {
  //         console.log(error);
  //       });
  //   }, 60000);

  // }

}
