import {UserRole} from "./userRole";

export class User {

  // @ts-ignore
  userId: string;

  email: string | undefined;

  firstName: string | undefined;

  lastName: string | undefined;

  role: UserRole | undefined;

}
