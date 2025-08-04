import {AgeType} from "@/components/BasicRegistration/dto/AgeType";

const FULL_AGE = 18;

export class DateUtil {

}


export function getTypeByBirthday(birthday): AgeType.ADULT | AgeType.CHILD {
    const age = this.calculateAge(birthday);
    return age <= FULL_AGE ? AgeType.ADULT : AgeType.CHILD;
}

export function calculateAge(birthday) {
    const today = new Date();
    const birthDate = new Date(birthday);
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();
    const dayDiff = today.getDate() - birthDate.getDate();

    if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
        age--;
    }

    return age;
}

export function formatDate(dateString): string {

    const date = new Date(dateString);

    const day = ('0' + date.getDate()).slice(-2);
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const year = date.getFullYear();

    return `${day}.${month}.${year}`;
}