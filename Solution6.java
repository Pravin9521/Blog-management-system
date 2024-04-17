class Solution6 {
    public static int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int maxArea=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                int currArea=0;
                int len=0;
                int bred=0;
                if(matrix[i][j]=='1'){
                    len=1;
                    bred=1;
                    int down=i;
                    int left=j;
                    while(left+1<n && matrix[i][left+1]=='1' ){
                        len+=1;
                        left++;
                    }
                    while(down+1<m && matrix[down+1][j]=='1'){
                        int left1=j;
                        while(left1+1<n && matrix[down+1][left1+1]=='1'){
                            left1++;
                        }
                        if(left1 >= j + len - 1){
                            bred++;
                        }
                        else{
                            break;
                        } 
                        down++;
                    }
                    int up=i;
                    while(up-1<m && up>0 && matrix[up-1][j]=='1'){
                        int left2=j;
                        while(left2+1<n && matrix[up-1][left2+1]=='1'){
                            left2++;
                        }
                        if(left2 >= j + len - 1){
                            bred++;
                        }
                        else{
                            break;
                        } 
                        up--;
                    }
                    currArea=len*bred;
                    if(maxArea<currArea){
                        maxArea=currArea;
                        System.out.print(i);
                        System.out.print(j);
                        System.out.println(maxArea);
                    }
                }
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        char[][] matrix = {
            {'1','1','1','1','1','1','1','1'},
            {'1','1','1','1','1','1','1','0'},
            {'1','1','1','1','1','1','1','0'},
            {'1','1','1','1','1','0','0','0'},
            {'0','1','1','1','1','0','0','0'}
        };
        System.out.println(maximalRectangle(matrix)); // Output: 6
    }
}
