import { User } from "../../../core/models/user.model";
import { Theme } from "../../theme/models/theme.model";

export class Story {
    
    id!: number;
    title!: string;
    content!: string;
    createdAt!: Date;
    associatedUser!: User;
    associatedTheme!: Theme;

}
