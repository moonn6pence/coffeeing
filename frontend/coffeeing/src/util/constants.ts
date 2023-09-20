export const API_URL = process.env.REACT_APP_BASE_API_URL;

export type Item = {
    value: number,
    name: string
}

export const GENDER_ITEMS = [
    {
        value:0,
        name: "남성"
    },
    {
        value:1,
        name: "여성"
    },
]

export const AGE_ITEMS = [
    {
        value:0,
        name: "10대"
    },
    {
        value:1,
        name: "20대"
    },
    {
        value:2,
        name: "30대"
    },    {
        value:3,
        name: "40대"
    },    {
        value:4,
        name: "50대"
    },    {
        value:5,
        name: "60대"
    },
];