import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-directory',
  templateUrl: './directory.component.html',
  styleUrls: ['./directory.component.css']
})
export class DirectoryComponent implements OnInit {

  users: any[] = [];
  filteredUsers: any[] = [];
  searchText: string = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe((data) => {
      this.users = data.value;
      this.filteredUsers = this.users;
    });
  }

  onSearchChange() {
    // Vérifie si les utilisateurs sont disponibles avant de filtrer
    if (this.users.length > 0) {
      // Filtrer les utilisateurs en fonction du texte saisi
      this.filteredUsers = this.users.filter(user =>
        user.firstName.toLowerCase().startsWith(this.searchText.toLowerCase()) ||
        user.lastName.toLowerCase().startsWith(this.searchText.toLowerCase())
      );
    }
  }
}
