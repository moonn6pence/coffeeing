export enum TagType {
    COFFEE_BEAN = "COFFEE_BEAN",
    COFFEE_CAPSULE = "COFFEE_CAPSULE",
}

export interface Tag {
    tagId: number,
    name: string,
    category: TagType,
}