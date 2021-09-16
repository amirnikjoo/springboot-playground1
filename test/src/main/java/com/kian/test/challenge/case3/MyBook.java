package com.kian.test.challenge.case3;

class MyBook extends Book {
    @Override
    void setTitle(String s) {
         this.title =s;
    }

    public static void main(String []args){
        //Book new_novel=new Book(); This line prHMain.java:25: error: Book is abstract; cannot be instantiated
        String title="A tale of two cities";
        MyBook new_novel=new MyBook();
        new_novel.setTitle(title);
        System.out.println("The title is: "+new_novel.getTitle());

    }
}
