export interface UserSession {
  
  token: string;
  id: number;
  name: string;
  email: string;
  password?: string;

}