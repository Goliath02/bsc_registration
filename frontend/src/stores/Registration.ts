
export interface RegistrationData {
  mainData: MainDetails;
  financialData: FinancialDetails;
  dataApproval: DataApproval;
}

export type ImageFile = File & { type: 'image/jpeg' | 'image/png'};

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
}

export interface DataApproval {
  dataProtection: boolean;
  dataStatute: boolean;
  dataCorrectness: boolean;
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
