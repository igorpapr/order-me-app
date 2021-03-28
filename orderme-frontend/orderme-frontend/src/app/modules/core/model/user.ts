import {UserRole} from "./userRole";

export class User {

  userId: string | undefined;

  email: string | undefined;

  password: string | undefined;

  firstName: string | undefined;

  lastName: string | undefined;

  role: UserRole | undefined;

}
