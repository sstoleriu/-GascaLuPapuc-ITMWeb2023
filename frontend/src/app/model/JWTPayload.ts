export class JWTPayload {
    id?: number;
    exp?: number;
    firstname?: string;
    lastname?: string;
    phonenumber?: string;
    sub?: string;
    roles?: string[];

    constructor(obj: any) {
        this.id = Number(obj.id)
        this.exp = Number(obj.exp)
        this.firstname = obj.firstname
        this.lastname = obj.lastname
        this.phonenumber = obj.phonenumber
        this.sub = obj.sub
        this.roles = obj.Authorities
    }
}