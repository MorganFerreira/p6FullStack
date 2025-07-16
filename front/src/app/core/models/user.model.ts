import { Theme } from "../../features/theme/models/theme.model";

export class User {
    
    id!: number;
    name!: string;
    email!: string;
    subscriptions!: Theme[];

}
