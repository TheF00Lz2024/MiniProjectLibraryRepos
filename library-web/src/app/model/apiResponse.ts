export interface loginAPI{
    message: string,
    token: string
}

export interface bookData{
    isbn: string;
    title: string;
    authorName: string;
    imgData: string;
}

export interface user{
    username: string,
    password: string,
    roles: string
}

export interface userRole{
    role: string
}

export interface formattedUser{
    username: string,
    roles: string
}