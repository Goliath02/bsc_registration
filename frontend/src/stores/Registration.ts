export interface RegistrationData {
  verificationFiles: ImageFile[] | null;
  mainData: MainDetails;
  financialData: FinancialDetails;
}

export type ImageFile = File & { type: "image/jpeg" | "image/png" };

export interface MainDetails {
  type: string;
  reason: string;
  name: string;
  surename: string;
  birthday: string;
  gender: string;
  email: string;
  phone: string;
  street: string;
  plz: string;
  place: string;
  entryDate: Date;
  morePersons: MorePersonDetails[];
}

export interface MorePersonDetails {
  name: string;
  surename: string;
  birthday: string;
  gender: string;
}

export interface FinancialDetails {
  iban: string;
  nameOfBankOwner: string;
  sureNameBankOwner: string;
  dataProtection: boolean;
  dataCorrectness: boolean;
  dataStatute: boolean;
  hiddenSecurityCheck: boolean;
}


export enum RegistrationType {
  MEMBER = "Mitglied",
  STUDENT = "Schüler/Student über 18",
  FAMILY = "Familie",
}

export enum AgeType {
  ADULT,
  CHILD,
  SENIOR,
}

export enum ErrorType {
  EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND",
  IMAGE_TOO_LARGE = "IMAGE_TOO_LARGE",
  INTERNAL = "INTERNAL_SERVER_ERROR",
  FORM_INVALID = "FORM_INVALID",
}
