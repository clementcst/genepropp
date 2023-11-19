import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-directory',
  templateUrl: './directory.component.html',
  styleUrls: ['./directory.component.css']
})
export class DirectoryComponent implements OnInit {

  users: any[] = [];

  constructor(private userService : UserService ) { 
    this.userService = userService;
  }

  ngOnInit(): void {
    this.userService.getUsers().subscribe((data) => {
      this.users = data.value; 
    });
  }

}
