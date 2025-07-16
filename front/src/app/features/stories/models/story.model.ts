import { User } from "../../../core/models/user.model";

export class Story {
    
    id!: number;
    title!: string;
    content!: string;
    createdAt!: Date;
    associatedUser!: User;
    associatedTheme!: number;

}
