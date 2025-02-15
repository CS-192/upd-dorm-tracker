//User related
export type Privilege= "management" | "dormer"

export interface User {
    username:string;
    password:string;
    privilege: Privilege;
    dorm: String
}


// Requests related
export type Time= {
    hours: number;
    minutes: number;
}

export interface Request_Type {
    type: string;
}

export interface Request {
    student_id: number;
    request_type: Request_Type;
    request_date: Date;
    request_time: Time;
    dorm: string;
}

export type Pass_type= "overnight" | "late night";

export interface Pass extends Request_Type{
    type: "Pass";
    pass_type: Pass_type;
    departure: Date;
    arrival: Date;
    arrival_time: Time
    
}

export interface Report extends Request_Type{
    type: "Report";
    details: string;
    comment: string;
    resolved: boolean;
}

export interface Billing extends Request_Type{
    type: "Billing";
    end_month: Date;
    resolved: boolean;

}


// Exit and Entry Related
export type dormer_or_guest= "dormer" | "guest"

export type entry_or_exit= "n"|"x";

export interface exit_and_entry{
    student_id:number;
    date: Date;
    time: Time;
    entry_exit: entry_or_exit;
    dorm: string;

}


//Dorm_Detail related
export interface Dorm_Detail_Type{
    type:string
}

export interface FAQ extends Dorm_Detail_Type{
    type:"FAQ";
    question:string;
    answer:string;
}

export interface Announcement extends Dorm_Detail_Type{
    type: "Announcement";
    date: Date;
    time: Time;
    subject: string;
    details: string;
    
}

export interface Dorm_Detail {
    detail_type: Dorm_Detail_Type;
    dorm: string;
}

//Dormer Related
export type sex='M' | 'F';

export interface Dormer {
    student_id: number;
    last_name: string;
    first_name: string;
    middile_initial: string;
    birth_date: Date;
    phone_number: number;
    dorm: string;
    room_number:string;
    address:string;

}

export interface Guardian{
    student_id: number;
    name: string;
    phone_number: number;
    address: string;
}

export interface Violation{
    student_id: number;
    violation: string;
    date: Date;
}