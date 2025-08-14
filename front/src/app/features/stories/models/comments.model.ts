import { User } from "../../../core/models/user.model";
import { Story } from "./story.model";

export class Comments {
    
    id!: number;
    content!: string;
    associatedUser!: User;
    associatedStory!: Story;

}
